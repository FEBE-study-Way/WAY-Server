package WAY.way.domain.room.entity;

import WAY.way.domain.member.entity.MemberEntity;
import WAY.way.domain.room.presentation.data.Kind;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "room")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = false , name = "floor")
    private Integer floor;

    @Enumerated(EnumType.STRING)
    @Column(name = "kind" , nullable = false)
    private Kind kind;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", nullable = false)
    private MemberEntity owner;
}
