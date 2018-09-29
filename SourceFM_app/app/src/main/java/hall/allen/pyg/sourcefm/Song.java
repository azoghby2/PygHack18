package hall.allen.pyg.sourcefm;

public class Song {


    private String id;
    private String name;
    private String singer;
    private int length;
    public Song(String id) {
        this.id = id;
        resolve_stats(id);
    }

    private void resolve_stats(String id) {
        //TODO spotidy api here

        this.name = name;
        this.singer = singer;
        this.length = length;
    }
}
