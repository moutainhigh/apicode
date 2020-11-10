package com.ycandyz.master.kafka;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Message {

    private String id;
    private Object msg;
    private Long time;
}
