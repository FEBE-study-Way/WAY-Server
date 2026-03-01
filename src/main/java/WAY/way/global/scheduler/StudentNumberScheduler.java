package WAY.way.global.scheduler;

import WAY.way.domain.member.entity.MemberEntity;
import WAY.way.domain.member.presentation.data.Role;
import WAY.way.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentNumberScheduler {

    private final MemberRepository memberRepository;

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

    private String upgradeStudentNumbers(String currentNumber) {
        int grade = Integer.parseInt(currentNumber.substring(0,1));
        String classAndNumber = currentNumber.substring(1);

        return (grade+1) + classAndNumber;
    }

    private void handleGraduation(MemberEntity student){
        memberRepository.deleteById(student.getId());
    }
}