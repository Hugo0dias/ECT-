public class ImageType extends Type {
    public ImageType() {
        super("Image");
    }

    @Override
    public String toString() {
        return "Image";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return true;
    }
    
    public boolean isImage(Object value) {
        if (value == null) {
            return false;
        }
        
        ///////////////  NÃO SEI SE É O CORRETO
        if (value instanceof String) {
            String path = ((String) value).toLowerCase();
            return path.endsWith(".pgm") || path.endsWith(".pbm") || 
                   path.endsWith(".ppm") || path.endsWith(".png") ||
                   path.endsWith(".jpg") || path.endsWith(".jpeg");
        }
        
        return false;
    }
}
