package com.astroweather;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Location location);

    @Update
    void update(Location location);

    @Delete
    void delete(Location location);

    @Query("SELECT * FROM locations ORDER BY id ASC")
    List<Location> getAllLocations();

    @Query("SELECT * FROM locations WHERE id LIKE :id LIMIT 1")
    List<Location> findById(String id);
}
