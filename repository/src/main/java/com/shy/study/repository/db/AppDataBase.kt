package com.shy.study.repository.db

import androidx.room.*
import com.shy.basic.application.BaseApplication
import com.shy.study.repository.dao.BannerDao
import com.shy.study.repository.entity.Banner
import com.shy.study.repository.entity.converter.BannerConverter

@Database(
    entities = [
        Banner::class
    ],
    version = 1
)
@TypeConverters(
    value = [
        BannerConverter::class
    ]
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun bannerDao(): BannerDao

    companion object {
        val instance = Single.sin

        /*private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                    create table Book (
                    id integer primary key autoincrement not null,
                    name text not null,
                    pages integer not null)
                """.trimIndent()
                )
            }
        }*/
    }

    private object Single {
        val sin: AppDataBase = Room.databaseBuilder(
            BaseApplication.instance(),
            AppDataBase::class.java,
            "data.db"
        )
            //.addMigrations(MIGRATION_1_2)
            .allowMainThreadQueries()
            .build()
    }
}
