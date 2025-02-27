package com.lira.automobileLira.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutomobileModel(@JsonAlias("modelos") List<AutomobileData> models) {

}
