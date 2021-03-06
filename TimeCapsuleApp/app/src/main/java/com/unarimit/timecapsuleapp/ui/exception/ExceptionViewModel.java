package com.unarimit.timecapsuleapp.ui.exception;

import androidx.lifecycle.ViewModel;

import com.unarimit.timecapsuleapp.entities.ExceptionInfo;
import com.unarimit.timecapsuleapp.utils.database.DbContext;

import java.util.Comparator;
import java.util.List;

public class ExceptionViewModel  extends ViewModel {
    List<ExceptionInfo> exceptionInfos;
    public ExceptionViewModel(){
        exceptionInfos = DbContext.ExceptionInfos.GetExceptionInfoList();
        if(exceptionInfos != null){
            exceptionInfos.sort(Comparator.comparing(ExceptionInfo::getDate).reversed());
        }
    }

    public List<ExceptionInfo> getExceptionInfos() {
        return exceptionInfos;
    }
}
