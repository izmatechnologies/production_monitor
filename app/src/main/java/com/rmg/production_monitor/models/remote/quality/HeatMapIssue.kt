package com.rmg.production_monitor.models.remote.quality

import com.google.gson.annotations.SerializedName

data class HeatMapIssue(
    @SerializedName("IssueId")
    val issueId: Int,
    @SerializedName("IssueName")
    val issueName: String
)