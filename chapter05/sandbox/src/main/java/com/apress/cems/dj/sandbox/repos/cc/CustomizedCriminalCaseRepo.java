package com.apress.cems.dj.sandbox.repos.cc;

        import com.apress.cems.dao.CriminalCase;
        import com.apress.cems.dao.Detective;

        import java.util.List;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface CustomizedCriminalCaseRepo {

    List<Detective> getTeam(CriminalCase criminalCase);
}
