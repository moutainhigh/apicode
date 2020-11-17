package com.ycandyz.master.domain.response.risk;

import lombok.Data;

import java.util.List;

@Data
public class TabooCheckRep {

    private List<String> phraseNames;

    private String message;
}
