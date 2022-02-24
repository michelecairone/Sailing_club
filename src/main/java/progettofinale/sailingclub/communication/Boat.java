package progettofinale.sailingclub.communication;

import java.io.Serializable;

/**
 * The class {@code Boat} is used to represent boat entity.
 * It contains property and methods used to manage boat.
 */
public class Boat implements Serializable {

    //    serialVersionUID - It is the versionUID
    static final long serialVersionUID = 1L;

    /**
     * Class fields.
     * id - It is the identification code of the boat.
     * name - It is the name of the boat.
     * length - It is the length of the boat.
     * owner - It is the owner of the boat.
     * storage - It is the boat storage fee.
     * dateStorage - It is the date of payment of the boat storage fee.
     */
    private int id;
    private String name;
    private int length;
    private String owner;
    private float storage;
    private String dateStorage;

    /**
     * Class constructor.
     *
     * @param id It is the identification code of the boat.
     * @param name It is the name of the boat.
     * @param length It is the length of the boat.
     * @param storage It is the identification code of the boat.
     * @param dateStorage It is the date of payment of the boat storage fee.
     */
    public Boat (final int id, final String name, final int length,final float storage, final String dateStorage ){

        this.setId(id);
        this.setName(name);
        this.setLength(length);
        this.setStorage(storage);
        this.setDateStorage(dateStorage);

    }


    /**
     * Class constructor.
     *
     * @param id It is the identification code of the boat.
     * @param name It is the name of the boat.
     * @param length It is the length of the boat.
     * @param storage It is the identification code of the boat.
     */
    public Boat (final int id, final String name, final int length, final float storage  ){

        this.setId(id);
        this.setStorage(storage);
        this.setName(name);
        this.setLength(length);

    }
    /**
     * Class constructor.
     *
     * @param id It is the identification code of the boat.
     * @param name It is the name of the boat.
     * @param length It is the length of the boat.
     */
    public Boat (final int id, final String name, final int length ){

        this.setId(id);
        this.setName(name);
        this.setLength(length);

    }

    /**
     * Class constructor.
     *
     * @param id It is the identification code of the boat.
     * @param name It is the name of the boat.
     * @param length It is the length of the boat.
     * @param owner It is the owner of the boat.
     */
    public Boat (final int id, final String name, final int length, final String owner ){

        this.setId(id);
        this.setName(name);
        this.setLength(length);
        this.setOwner(owner);

    }

    /**
     * Class constructor.
     *
     * @param name It is the name of the boat.
     * @param length It is the length of the boat.
     * @param owner It is the owner of the boat.
     */
    public Boat ( final String name, final int length, final String owner ){

        this.setName(name);
        this.setLength(length);
        this.setOwner(owner);

    }

    /**
     * Class constructor.
     * to instantiate new Boat objects without parameters.
     */
    public Boat() {}

    /**
     * This method is used to get the Boat's identification code.
     *
     * @return It returns the Boat's identification code.
     */
    public int getId() { return this.id; }

    /**
     * This method is used to get the Boat's name.
     *
     * @return It returns the Boat's name.
     */
    public String getName() { return this.name; }

    /**
     * This method is used to get the Boat's height.
     *
     * @return It returns the Boat's height.
     */
    public int getLength() { return this.length; }

    /**
     * This method is used to get the Boat's owner.
     *
     * @return It returns the Boat's owner.
     */
    public String getOwner() { return owner; }

    /**
     * This method is used to get the Boat's storage fee.
     *
     * @return It returns the Boat's storage fee.
     */
    public float getStorage() {
        return storage;
    }

    /**
     * This method is used to get the date of payment of the boat storage fee.
     *
     * @return It returns the date of payment of boat the storage fee.
     */
    public String getDateStorage() {
        return dateStorage;
    }

    /**
     * This method is used to set the Boat's identification code.
     *
     * @param id is the Boat's identification code.
     */
    public void setId(int id) { this.id = id; }

    /**
     * This method is used to set the Boat's name.
     *
     * @param name is the Boat's name.
     */
    public void setName(String name) { this.name = name; }

    /**
     * This method is used to set the Boat's length.
     *
     * @param length is the Boat's length.
     */
    public void setLength(int length) { this.length = length; }

    /**
     * This method is used to set the Boat's owner.
     *
     * @param owner is the Boat's owner.
     */
    public void setOwner(String owner) { this.owner = owner; }

    /**
     * This method is used to set the Boat's storage fee.
     *
     * @param storage is the Boat's storage fee.
     */
    public void setStorage(float storage) {
        this.storage = storage ;
    }

    /**
     * This method is used to set the date of payment of the boat storage fee.
     *
     * @param dateStorage is the date of payment of boat the storage fee.
     */
    public void setDateStorage(String dateStorage) {
        this.dateStorage = dateStorage;
    }

}
