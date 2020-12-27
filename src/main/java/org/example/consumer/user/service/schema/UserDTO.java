package org.example.consumer.user.service.schema;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

/**
 * Note: The consumer is not interested in all data of the provider's tasks. The consumer's tasks only
 * include id, title and content, but not the additional tag and the createdAt data from the provider's tasks.
 * Therefore, when specifying pacts for the provider, this consumer will completely ignore those additional fields,
 * because those are not his business.
 */
@Data
@NoArgsConstructor
public class UserDTO {

    @NonNull
    private String userId;

    @NonNull
    private String userName;

    @NonNull
    private List<SkillDTO> skillDtos;

}
