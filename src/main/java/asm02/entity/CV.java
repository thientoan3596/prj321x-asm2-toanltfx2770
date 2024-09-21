package asm02.entity;


import asm02.dto.CVResponse;
import lombok.*;
import org.hibernate.LazyInitializationException;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "cv")
@EqualsAndHashCode(exclude = { "user"})
@ToString(exclude = { "user"})
public class CV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "file_name")
    private String fileName;
    @Column(name="file_name_on_server")
    private String fileNameOnServer;
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Column(name = "is_default")
    private Boolean isDefault;

    /**
     * @throws IllegalStateException if being called outside Transaction
     */
    public CVResponse toResponse(){
        try{
            return CVResponse.builder()
                    .id(id)
                    .isDefault(isDefault)
                    .fileName(fileName)
                    .fileNameOnServer(fileNameOnServer)
                    .build();
        }catch (Exception e){
            if(e instanceof LazyInitializationException)
                throw new IllegalStateException("Lazy loading outside Tx");
            throw e;
        }
    }
}
