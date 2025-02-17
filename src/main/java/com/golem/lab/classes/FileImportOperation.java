package com.golem.lab.classes;

import com.golem.lab.model.Client;
import jakarta.persistence.*;

@Entity
@Table(name = "FileImportOperations")
public class FileImportOperation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fio_seq")
    @SequenceGenerator(name = "fio_seq", sequenceName = "fio_seq")
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client; //Поле может быть null

    @Column(nullable = false)
    private boolean finished;

    @Column(nullable = false)
    private int objectsImported;

    @Column(nullable = false)
    private boolean visible = true;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getObjectsImported() {
        return objectsImported;
    }

    public void setObjectsImported(int objectsImported) {
        this.objectsImported = objectsImported;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
