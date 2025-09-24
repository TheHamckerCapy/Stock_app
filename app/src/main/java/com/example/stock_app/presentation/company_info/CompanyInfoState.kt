package com.example.stock_app.presentation.company_info

import com.example.stock_app.domain.model.CompanyInfo
import com.example.stock_app.domain.model.IntradayInfo

data class CompanyInfoState (
    val stockInfos: List<IntradayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)