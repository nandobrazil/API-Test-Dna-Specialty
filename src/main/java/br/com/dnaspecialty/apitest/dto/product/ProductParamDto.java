package br.com.dnaspecialty.apitest.dto.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class ProductParamDto {
        @NotBlank
        private String name;

        @NotBlank
        private Integer price;

        public ProductParamDto() {
        }

        @JsonCreator
        public ProductParamDto(
                @JsonProperty("name") String name,
                @JsonProperty("price") Integer price) {
            this.setName(name);
            this.setPrice(price);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }
}
