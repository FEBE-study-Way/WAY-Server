package WAY.way.domain.room.entity;

import WAY.way.domain.member.entity.MemberEntity;
import WAY.way.domain.room.presentation.data.Kind;
import jakarta.persistence.*;
import lombok.*;

/**
 * 예약 가능한 공간(강의실, 기숙사 등)을 나타내는 JPA 엔티티.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "room")
public class RoomEntity {

    /** 공간 고유 ID (PK, 자동 생성). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    /** 공간 이름. */
    @Column(name = "name", nullable = false)
    private String name;

    /** 공간이 위치한 층. */
    @Column(nullable = false , name = "floor")
    private Integer floor;

    /** 공간 유형 ({@link Kind#MAIN} 또는 {@link Kind#DONGHANG} 또는 {@link Kind#GEUMBONG}). */
    @Enumerated(EnumType.STRING)
    @Column(name = "kind" , nullable = false)
    private Kind kind;

    /** 공간 담당 교사. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    private MemberEntity owner;

    public void assignOwner(MemberEntity owner) {
        this.owner = owner;
    }
}
