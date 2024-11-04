package com.lira.automobileLira.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AutomobileData(@JsonAlias("codigo") String code,
                             @JsonAlias("nome") String name) {
}
