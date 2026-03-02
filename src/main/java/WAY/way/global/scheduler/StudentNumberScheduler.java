package WAY.way.global.scheduler;

import WAY.way.domain.member.entity.MemberEntity;
import WAY.way.domain.member.presentation.data.Role;
import WAY.way.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 학번 진급 처리 스케줄러.
 * <p>
 * 매년 1월 7일 자정({@code 0 0 0 7 1 *})에 실행되어 {@link Role#USER} 회원의 학년을 한 단계 올린다.
 * 3학년 이상인 경우 졸업으로 처리하여 회원 정보를 삭제한다.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class StudentNumberScheduler {

    private final MemberRepository memberRepository;

    /**
     * 모든 {@link Role#USER} 회원의 학번을 진급 처리한다.
     * <ul>
     *   <li>학년이 3 이상이면 졸업({@link #handleGraduation(MemberEntity)}) 처리</li>
     *   <li>그 외에는 학년을 1 증가</li>
     * </ul>
     * 학번이 없거나 형식이 올바르지 않은 회원은 건너뛴다.
     */
    @Scheduled(cron = "0 0 0 7 1 *")
    @Transactional
    public void upgradeStudentNumbers() {

        List<MemberEntity> memberEntities = memberRepository.findAllByRole(Role.USER);

        int upgraded = 0;
        int graduated = 0;

        for (MemberEntity memberEntity : memberEntities) {
            String currentNumber = memberEntity.getStudentNumber();

            if(currentNumber==null || currentNumber.length() !=4) {
                continue;
            }

            int currentGrade = Integer.parseInt(currentNumber.substring(0,1));

            if(currentGrade >= 3){
                handleGraduation(memberEntity);
                graduated++;
            } else{
                String newNumber = upgradeStudentNumbers(currentNumber);
                memberEntity.updateStudentNumber(newNumber);
                upgraded++;
            }
        }
    }

    /**
     * 학번의 학년을 1 증가시킨 새 학번을 반환한다.
     *
     * @param currentNumber 현재 학번 4자리 문자열
     * @return 학년이 1 증가된 새 학번
     */
    private String upgradeStudentNumbers(String currentNumber) {
        int grade = Integer.parseInt(currentNumber.substring(0,1));
        String classAndNumber = currentNumber.substring(1);

        return (grade+1) + classAndNumber;
    }

    /**
     * 졸업 처리: 회원 정보를 삭제한다.
     *
     * @param student 졸업 처리할 회원 엔티티
     */
    private void handleGraduation(MemberEntity student){
        memberRepository.deleteById(student.getId());
    }
}