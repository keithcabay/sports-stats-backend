package com.keith.SportsStats.domains.dto;

import com.keith.SportsStats.domains.entity.PlayersEntity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamsDto {
    String shortName;

    String fullName;
}
