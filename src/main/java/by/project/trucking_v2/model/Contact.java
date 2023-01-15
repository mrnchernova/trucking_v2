package by.project.trucking_v2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Contact implements Serializable {

    public Integer unp = 40001911;
    public String phone = "+375(29)222-33-33";

    @Override
    public String toString() {
        return "УНП: " + unp + ", тел. " + phone;
    }
}