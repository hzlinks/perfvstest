package com.haorld.perfvs.sdemo.dao;

import com.haorld.perfvs.sdemo.model.ItemDo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Sql(scripts = "/schema_sqlite.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ItemDaoTest {

    @Resource
    private ItemDao itemDao;

    @Test
    void testCreateItem() {
        // Given
        ItemDo item = new ItemDo();
        item.setItemName("Test Item");
        item.setMarketPrice(100);

        // When
        boolean result = itemDao.createItem(item);

        // Then
        assertTrue(result);
    }

    @Test
    void testGetItemById() {
        // Given
        ItemDo item = new ItemDo();
        item.setItemName("Test Item");
        item.setMarketPrice(100);
        itemDao.createItem(item);

        // When
        ItemDo retrievedItem = itemDao.getItemById(1L);

        // Then
        assertNotNull(retrievedItem);
        assertEquals("Test Item", retrievedItem.getItemName());
        assertEquals(100, retrievedItem.getMarketPrice());
    }

    @Test
    void testGetAllItems() {
        // Given
        ItemDo item1 = new ItemDo();
        item1.setItemName("Test Item 1");
        item1.setMarketPrice(100);
        itemDao.createItem(item1);

        ItemDo item2 = new ItemDo();
        item2.setItemName("Test Item 2");
        item2.setMarketPrice(200);
        itemDao.createItem(item2);

        // When
        List<ItemDo> items = itemDao.getAllItems();

        // Then
        assertNotNull(items);
        assertTrue(items.size() >= 2);
    }

    @Test
    void testUpdateItem() {
        // Given
        ItemDo item = new ItemDo();
        item.setItemName("Original Name");
        item.setMarketPrice(100);
        itemDao.createItem(item);

        item.setItemName("Updated Name");
        item.setMarketPrice(200);

        // When
        boolean result = itemDao.updateItem(item);

        // Then
        assertTrue(result);

        ItemDo updatedItem = itemDao.getItemById(item.getItemId());
        assertEquals("Updated Name", updatedItem.getItemName());
        assertEquals(200, updatedItem.getMarketPrice());
    }

    @Test
    void testDeleteItem() {
        // Given
        ItemDo item = new ItemDo();
        item.setItemName("Test Item");
        item.setMarketPrice(100);
        itemDao.createItem(item);

        // When
        boolean result = itemDao.deleteItem(item.getItemId());

        // Then
        assertTrue(result);

        ItemDo deletedItem = itemDao.getItemById(item.getItemId());
        assertNull(deletedItem);
    }
}
