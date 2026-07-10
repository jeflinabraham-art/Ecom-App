package com.app.ecom.Model;

import com.app.ecom.Enum.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role = UserRole.CUSTOMER;

//    Cascade means:
//    Whatever operation is performed on the User, perform the same operation on its Address. (save, delete, update)

//    orphanRemoval = true means:
//    If an Address is removed from its parent User, delete it from the database.

//    User user = userRepository.findById(1L).get();
//    user.setAddress(null);
//    userRepository.save(user);
//    The address is no longer referenced by any user—it has become an orphan. (Hibernate deletes it if orphanRemoval = true)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;


    // below field need not be passed to JSON, jpa inserts these fields to the table automatically.
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
