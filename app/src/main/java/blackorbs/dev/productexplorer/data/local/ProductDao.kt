package blackorbs.dev.productexplorer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import blackorbs.dev.productexplorer.data.entities.Product

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(products: List<Product>)

    @Query("SELECT * FROM products WHERE id=:id")
    suspend fun get(id: Int): Product

    @Query("SELECT * FROM products")
    suspend fun getAll(): List<Product>
}