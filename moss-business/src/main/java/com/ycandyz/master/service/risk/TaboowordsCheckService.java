package com.ycandyz.master.service.risk;


import com.ycandyz.master.api.ReturnResponse;
import com.ycandyz.master.domain.query.risk.TabooCheckNotInsertParams;
import com.ycandyz.master.domain.query.risk.TabooCheckParams;

public interface TaboowordsCheckService {
    ReturnResponse check(TabooCheckParams tabooCheckParams);
    ReturnResponse checkNotInsert(TabooCheckNotInsertParams tabooCheckParams);
}
