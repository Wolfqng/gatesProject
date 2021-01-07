package gatesProject;

public class cable
{
    private boolean powered;
    private pwrNode inp;
    private pwrNode out;
    
    public cable(pwrNode inp, pwrNode out) {
        this.inp = returnInp(inp, out);
        this.out = returnOut(inp, out);
        
        inp.addCable(this);
        out.addCable(this);
    }
    
    public boolean isPowered() {
        if(inp.isPowered()) {
            this.powered = true;
            return true;
        }  
        this.powered = false;
        return false;
    }
    
    private pwrNode returnInp(pwrNode inp, pwrNode out){
        if(inp.isInput()) return inp;
        return out;
    }
    
    private pwrNode returnOut(pwrNode inp, pwrNode out){
        if(inp.isInput()) return out;
        return inp;
    }
    
    public pwrNode getInp() {
        return inp;
    }
    
    public pwrNode getOut() {
        return out;   
    }
    
    public void setInp(pwrNode inp) {
       if(inp != null)inp.addCable(this);
       this.inp = inp; 
    }
    
    public void setOut(pwrNode out) {
        if(out != null)out.addCable(this);
        this.out = out;
    }
    
    public String toString() {
        return "Powered: " + this.powered;
    }
}

