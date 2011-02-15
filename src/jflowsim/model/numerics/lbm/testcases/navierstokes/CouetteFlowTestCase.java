package jflowsim.model.numerics.lbm.testcases.navierstokes;

import jflowsim.model.numerics.BoundaryCondition;
import jflowsim.model.numerics.UniformGrid;
import jflowsim.model.numerics.lbm.LBMMovingWallBC;
import jflowsim.model.numerics.lbm.LBMNoSlipBC;
import jflowsim.model.numerics.lbm.LbEQ;
import jflowsim.model.numerics.lbm.navierstokes.LBMNavierStokesGrid;
import jflowsim.model.numerics.lbm.testcases.TestCase;

public class CouetteFlowTestCase extends TestCase {

    public UniformGrid getGrid() {

        LBMNavierStokesGrid grid = new LBMNavierStokesGrid(0.5 /* length */, 0.5 /* width */, 0.005 /* dx */);

        grid.testcase = this.getClass().getSimpleName();

        grid.setGravity(0.0, 0.0 /* m/s^2 */);
        grid.setViscosity(0.005 /* m^2/s */);
        grid.setTimeStep(0.0001 /* s */);

        grid.updateLBParameters();

        this.initFluid(grid);

        grid.periodicX = true;

        grid.addBC(new LBMMovingWallBC(grid, BoundaryCondition.NORTH, 0.1, 0.0));
        grid.addBC(new LBMNoSlipBC(grid, BoundaryCondition.SOUTH));

        // Initial conditions
        double[] feq = new double[9];

        for (int i = 0; i < grid.nx; i++) {
            for (int j = 0; j < grid.ny; j++) {

                LbEQ.getBGKEquilibrium(1., 0., 0., feq);

                for (int dir = 0; dir < 9; dir++) {
                    grid.f[(i + j * grid.nx) * 9 + dir] = feq[dir];
                    grid.ftemp[(i + j * grid.nx) * 9 + dir] = feq[dir];
                }
            }
        }

        return grid;

    }
}