package com.learning.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Validation {
    private Integer txrecordSeq;
    private String txrecordHashcode;
    private Date validatedTimeStart;
    private Date validatedTimeEnd;
    private Date validatedResultTime;
}
