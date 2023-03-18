package com.example.practicemvc.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Item {

    @NotNull(groups = UpdateCheck.class) // 수정할때만
    private Long id;

    @NotBlank(groups = {SaveCheck.class, UpdateCheck.class}) // 전부 체크 한다.
    private String itemName;

    @NotNull(groups = {SaveCheck.class, UpdateCheck.class}) // 전부 체크 한다.
    @Range(min = 1000, max = 1000000, groups = {SaveCheck.class, UpdateCheck.class})  // 전부 체크 한다.
    private Integer price;

    @NotNull(groups = {SaveCheck.class, UpdateCheck.class}) // 전부 체크 한다.
    @Max(value = 9999, groups = {SaveCheck.class}) // 저장할때만
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
