package com.faisal.quc.core.extention

import android.content.Context
import android.widget.Toast
import com.faisal.quc.core.QCApplication


fun String.toast(context:Context = QCApplication.getContext()) {
            Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
    }