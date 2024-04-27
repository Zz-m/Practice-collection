package com.demo.bleapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity (tableName = "user_table")
class Users(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") @NotNull var id: Int,
            @ColumnInfo(name = "IsMan") @NotNull var IsMan: Int,
            @ColumnInfo(name = "IsLookingForMan") @NotNull var IsLookingForMan: Int,
            @ColumnInfo(name = "FriendsToTalk") @NotNull var FriendsToTalk: Int,
            @ColumnInfo(name = "LongRelationsship") @NotNull var LongRelationsship: Int,
            @ColumnInfo(name = "TouristInTown") @NotNull var TouristInTown: Int,
            @ColumnInfo(name = "JustSexualIntentions") @NotNull var JustSexualIntentions: Int,
            @ColumnInfo(name = "ProfilSliderPosition") @NotNull var ProfilSliderPosition: Int,
            @ColumnInfo(name = "Parameters") @NotNull var Parameters: String,
            @ColumnInfo(name = "LookingForSliderPosition") @NotNull var LookingForSliderPosition: Int
) {
}