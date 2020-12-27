package org.example.user.service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Note: The consumer is not interested in all data of the provider's tasks. The consumer's tasks only
 * include id, title and content, but not the additional tag and the createdAt data from the provider's tasks.
 * Therefore, when specifying pacts for the provider, this consumer will completely ignore those additional fields,
 * because those are not his business.
 */
@Data
@NoArgsConstructor
public class SkillDTO {

    @NonNull
    private String skillId;

    @NonNull
    private String skillName;

}
