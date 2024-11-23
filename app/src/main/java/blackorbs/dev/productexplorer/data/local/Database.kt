package blackorbs.dev.productexplorer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import blackorbs.dev.productexplorer.data.entities.Product

@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = false
)
abstract class Database: RoomDatabase() {
    abstract fun productDao(): ProductDao
}