package com.dhananjay.recyclerviewwithbuttonsincardview.app;

import com.dhananjay.recyclerviewwithbuttonsincardview.models.Result;

public interface OnRecordEventListener {

    void accept(Result result);

    void decline(Result result);
}
