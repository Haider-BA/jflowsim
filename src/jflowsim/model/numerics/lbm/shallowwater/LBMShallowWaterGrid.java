package jflowsim.model.numerics.lbm.shallowwater;

import jflowsim.model.numerics.lbm.LBMUniformGrid;
import jflowsim.model.numerics.utilities.Scalar;
import jflowsim.view.headupdisplay.HeadUpDisplay;

public class LBMShallowWaterGrid extends LBMUniformGrid {

    public LBMShallowWaterGrid(double _length, double _width, double _dx) {
        super(_length, _width, _dx);
    }

    public LBMShallowWaterGrid(double _length, double _width, int _nx, int _ny) {
        super(_length, _width, _nx, _ny);
    }

    protected void allocateMemory() {
        f = new double[nx * ny * 9];
        ftemp = new double[nx * ny * 9];
        type = new int[nx * ny];

        System.out.println("LBMShallowWaterGrid::allocateMemoery() nx:" + nx + " ny:" + ny + " - " + nx * ny);
    }

    public double getScalar(int x, int y, int type) {

        if (type == Scalar.V_X) {
            return getVeloX(x, y);
        } else if (type == Scalar.V_Y) {
            return getVeloY(x, y);
        } else if (type == Scalar.V) {
            return Math.sqrt(Math.pow(getVeloX(x, y), 2.0) + Math.pow(getVeloY(x, y), 2.0));
        } else if (type == Scalar.RHO) {
            return getDensity(x, y);
        } else if (type == Scalar.GRID_TYPE) {
            return this.getType(x, y);
        } else if (type == Scalar.T) {
            return 0.0;
        } else {
            System.out.println("unknown scalar value " + type);
            System.exit(-1);
            return -1;
        }
    }

    public void updateHeadUpDisplay(HeadUpDisplay hud) {
        hud.drawText("Viscosity: " + this.viscosity);
        hud.drawText("Gravity: " + this.gravityX + " " + this.gravityY);
        hud.drawText("Time step" + this.dt );
        hud.drawText("LBM viscosity: " + this.nue_lbm);
        hud.drawText("LBM forcing: " + this.forcingX1 + "," + this.forcingX2);
        hud.drawText("V scale: " + this.v_scale);
    }
}