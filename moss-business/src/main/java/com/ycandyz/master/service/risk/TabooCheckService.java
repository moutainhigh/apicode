package com.ycandyz.master.service.risk;


import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface TabooCheckService {

    List<String> check(String txt);
}
