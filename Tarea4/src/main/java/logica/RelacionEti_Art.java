package logica;

/**
 * Created by Leny96 on 20/6/2017.
 */
public class RelacionEti_Art {
    private long id;
    private long id_Eti;
    private long id_Art;

    public RelacionEti_Art(long id,long id_Art,long id_Eti) {
        this.id = id;
        this.id_Eti = id_Eti;
        this.id_Art = id_Art;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_Eti() {
        return id_Eti;
    }

    public void setId_Eti(long id_Eti) {
        this.id_Eti = id_Eti;
    }

    public long getId_Art() {
        return id_Art;
    }

    public void setId_Art(long id_Art) {
        this.id_Art = id_Art;
    }
}
