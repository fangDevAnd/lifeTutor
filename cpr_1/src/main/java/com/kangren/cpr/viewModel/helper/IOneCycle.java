package com.kangren.cpr.viewModel.helper;

import com.kangren.cpr.viewModel.CprOneCycleData;

public interface IOneCycle {
    void completed(CprOneCycleData currentData);

    void pressUpdate(CprOneCycleData currentData, SoundType st);

    void blowUpdate(CprOneCycleData currentData, SoundType st);
}
