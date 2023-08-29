package cc.robotdreams.lessons.lesson25;

import cc.robotdreams.automation.DB;
import cc.robotdreams.automation.Session;
import cc.robotdreams.automation.base.BaseTestNG;
import cc.robotdreams.automation.db.Category;
import org.testng.annotations.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JDBCTest extends BaseTestNG
{
    @Test
    public void test() {
        List<Category.Item> topCategories = DB.category.getTopCategories();
        for (Category.Item category : topCategories) {
            System.out.println(category.id + " - " + category.name);
        }

        List<Category.Item> allCategories = DB.category.getAllCategories();
        for (Category.Item child : allCategories)
            printItem("-", child);

        try {
            PreparedStatement statement = Session.get().mysql().preparedStatement("SELECT * FROM oc_category WHERE parent_id=?;");
            statement.setInt(1, 25);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                System.out.println(resultSet.getInt("category_id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void printItem(String prefix, Category.Item item) {
        System.out.println(prefix + " " + item.name);
        for (Category.Item child : item.childs())
            printItem(prefix + "-", child);
    }
}
