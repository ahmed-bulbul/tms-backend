package com.tms.common.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "actions")
public class Action {
    @Id
    private Integer id;

    private String actionName;

    public static Action withId(Integer id) {
        Action action = new Action();
        action.setId(id);
        return action;
    }
}
