package com.example.rickandmorty.domain.model

import com.example.rickandmorty.data.source.remote.dto.Location
import com.example.rickandmorty.data.source.remote.dto.Origin

data class Characters(
    val id: Int,
    val name: String,
    val image: String,
    val specie: String
)
