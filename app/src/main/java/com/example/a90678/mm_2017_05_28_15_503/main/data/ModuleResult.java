package com.example.a90678.mm_2017_05_28_15_503.main.data;

import com.example.a90678.lkx_common_17_05_17_16_45.common.bean.BaseListResult;

import java.util.List;

/**
 * Created by 90678 on 2017/6/25.
 */

public class ModuleResult extends BaseListResult<ModuleResult.Module> {

    @Override
    public List<Module> getList() {
        return module;
    }

    List<Module> module;

    public class Module {
        String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
