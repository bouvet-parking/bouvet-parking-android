package no.bouvet.projectparking.models

data class User(
        val uid : String?,
        val phone : String?,
        val cars : List<String>?
)