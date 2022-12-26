package com.example.bonialbrochures.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bonialbrochures.data.local.model.BrochureEntity

@Database(entities = [BrochureEntity::class], version = 1)
abstract class BrochureDatabase : RoomDatabase() {

    abstract fun brochureDao(): BrochureDao
}