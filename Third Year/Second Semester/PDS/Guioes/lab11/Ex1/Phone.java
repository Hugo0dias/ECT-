

public class Phone {
    
    private String Processor;
    private int Preco;
    private int Memory;
    private String Camera;

    public Phone(String Processor, int Preco, int Memory, String Camera) {
        this.Processor = Processor;
        this.Preco = Preco;
        this.Memory = Memory;
        this.Camera = Camera;
    }

    public String getProcessor() {
        return Processor;
    }

    public void setProcessor(String processor) {
        this.Processor = processor;
    }

    public int getPreco() {
        return Preco;
    }

    public void setPreco(int preco) {
        this.Preco = preco;
    }

    public int getMemory() {
        return Memory;
    }

    public void setMemory(int memory) {
        this.Memory = memory;
    }

    public String getCamera() {
        return Camera;
    }

    public void setCamera(String camera) {
        this.Camera = camera;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "Processor='" + Processor + '\'' +
                ", Preco=" + Preco +
                ", Memory=" + Memory +
                ", Camera='" + Camera + '\'' +
                '}';
    }
}

