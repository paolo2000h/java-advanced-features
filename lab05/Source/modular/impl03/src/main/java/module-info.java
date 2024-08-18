import ex.api.AnalysisService;
import pl.edu.pwr.service3.Accuracy;
import pl.edu.pwr.service3.Kappa;
import pl.edu.pwr.service3.MeanPrecision;
import pl.edu.pwr.service3.MeanRecall;

module impl03 {
    requires api;
    exports pl.edu.pwr.service3;
    provides AnalysisService with Accuracy, Kappa, MeanPrecision, MeanRecall;
}