module com.github.alvader01 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires java.sql;
    requires java.desktop;
    requires org.apache.pdfbox;

    opens com.github.alvader01.Entities to org.hibernate.orm.core, javafx.base;
    opens com.github.alvader01 to javafx.fxml;
    exports com.github.alvader01;
    exports com.github.alvader01.View;
    opens com.github.alvader01.View to javafx.fxml;
}
