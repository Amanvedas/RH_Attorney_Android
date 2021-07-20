package com.rafayee.RHAttorney.MenuModule.Model

data class CompanyInformation(
    val busineessHoursTotal: List<BusineessHoursTotal>,
    val links: Links,
    val locations: List<Location>,
    val phoneNumber: String
)