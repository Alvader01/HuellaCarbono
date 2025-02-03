module com.github.alvader01 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires java.sql;

    opens com.github.alvader01.Entities to org.hibernate.orm.core;
    opens com.github.alvader01 to javafx.fxml;
    exports com.github.alvader01;
    exports com.github.alvader01.View;
    opens com.github.alvader01.View to javafx.fxml;
}
