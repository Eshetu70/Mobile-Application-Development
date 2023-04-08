package edu.uncc.inclass09;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {Grade.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract GradeDao gradesDB() ;
}
