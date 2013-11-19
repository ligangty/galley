package org.commonjava.maven.galley.maven.model.view;

import static org.commonjava.maven.galley.maven.model.view.XPathManager.A;
import static org.commonjava.maven.galley.maven.model.view.XPathManager.AND;
import static org.commonjava.maven.galley.maven.model.view.XPathManager.END_PAREN;
import static org.commonjava.maven.galley.maven.model.view.XPathManager.EQQUOTE;
import static org.commonjava.maven.galley.maven.model.view.XPathManager.G;
import static org.commonjava.maven.galley.maven.model.view.XPathManager.NOT;
import static org.commonjava.maven.galley.maven.model.view.XPathManager.OPEN_PAREN;
import static org.commonjava.maven.galley.maven.model.view.XPathManager.OR;
import static org.commonjava.maven.galley.maven.model.view.XPathManager.QUOTE;
import static org.commonjava.maven.galley.maven.model.view.XPathManager.RESOLVE;
import static org.commonjava.maven.galley.maven.model.view.XPathManager.TEXT;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.commonjava.maven.atlas.ident.DependencyScope;
import org.commonjava.maven.atlas.ident.ref.ArtifactRef;
import org.commonjava.maven.atlas.ident.ref.VersionlessArtifactRef;
import org.commonjava.maven.atlas.ident.version.InvalidVersionSpecificationException;
import org.commonjava.maven.galley.maven.GalleyMavenException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DependencyView
    extends MavenGAVView
{

    private static final String C = "classifier";

    private static final String T = "type";

    private static final String S = "scope";

    private static final String OPTIONAL = "optional";

    private static final String EXCLUSIONS = "exclusions/exclusion";

    private String classifier;

    private String type;

    private DependencyScope scope;

    private Boolean optional;

    private Set<ProjectRefView> exclusions;

    public DependencyView( final MavenPomView pomView, final Element element )
    {
        super( pomView, element, "dependencyManagement/dependencies/dependency" );
    }

    public boolean isManaged()
        throws GalleyMavenException
    {
        return pomView.resolveXPathToNodeFrom( element, "ancestor::dependencyManagement", true ) != null;
    }

    public synchronized String getClassifier()
    {
        if ( classifier == null )
        {
            classifier = getValue( C );
        }

        return classifier;
    }

    public synchronized String getRawType()
    {
        if ( type == null )
        {
            type = getValue( T );
        }

        return type;
    }

    public synchronized String getType()
    {
        final String type = getRawType();
        return type == null ? "jar" : type;
    }

    public synchronized DependencyScope getScope()
        throws GalleyMavenException
    {
        if ( scope == null )
        {
            final String s = getValueWithManagement( S );
            scope = DependencyScope.getScope( s );
        }

        return scope == null ? DependencyScope.compile : scope;
    }

    public synchronized boolean isOptional()
    {
        if ( optional == null )
        {
            final String val = getValue( OPTIONAL );
            optional = val == null ? false : Boolean.parseBoolean( val );
        }

        return optional;
    }

    public synchronized Set<ProjectRefView> getExclusions()
        throws GalleyMavenException
    {
        if ( exclusions == null )
        {
            final List<Node> nodes = getFirstNodesWithManagement( EXCLUSIONS );
            if ( nodes != null )
            {
                final Set<ProjectRefView> exclusions = new HashSet<>();
                for ( final Node node : nodes )
                {
                    exclusions.add( new MavenGAView( pomView, (Element) node ) );
                }

                this.exclusions = exclusions;
            }
        }

        return exclusions;
    }

    @Override
    protected String getManagedViewQualifierFragment()
    {
        final StringBuilder sb = new StringBuilder();

        sb.append( RESOLVE )
          .append( G )
          .append( TEXT )
          .append( END_PAREN )
          .append( EQQUOTE )
          .append( getGroupId() )
          .append( QUOTE )
          .append( AND )
          .append( RESOLVE )
          .append( A )
          .append( TEXT )
          .append( END_PAREN )
          .append( EQQUOTE )
          .append( getArtifactId() )
          .append( QUOTE );

        final String cls = getClassifier();
        if ( cls != null )
        {
            sb.append( AND )
              .append( RESOLVE )
              .append( C )
              .append( TEXT )
              .append( END_PAREN )
              .append( EQQUOTE )
              .append( cls )
              .append( QUOTE );
        }
        else
        {
            sb.append( AND )
              .append( NOT )
              .append( C )
              .append( END_PAREN );
        }

        final String type = getRawType();
        if ( type != null )
        {
            sb.append( AND )
              .append( RESOLVE )
              .append( T )
              .append( TEXT )
              .append( END_PAREN )
              .append( EQQUOTE )
              .append( type )
              .append( QUOTE );
        }
        else
        {
            sb.append( AND )
              .append( OPEN_PAREN )
              .append( NOT )
              .append( T )
              .append( END_PAREN )
              .append( OR )
              .append( RESOLVE )
              .append( T )
              .append( TEXT )
              .append( END_PAREN )
              .append( EQQUOTE )
              .append( "jar" )
              .append( QUOTE )
              .append( END_PAREN );
        }

        return sb.toString();
    }

    public ArtifactRef asArtifactRef()
        throws GalleyMavenException
    {
        try
        {
            return new ArtifactRef( asProjectVersionRef(), getType(), getClassifier(), isOptional() );
        }
        catch ( IllegalArgumentException | InvalidVersionSpecificationException e )
        {
            final String classifier = getClassifier();
            throw new GalleyMavenException( "Cannot render ArtifactRef: %s:%s:%s:%s%s. Reason: %s", e, getGroupId(), getArtifactId(), getVersion(),
                                            getRawType(), ( classifier == null ? "" : ":" + classifier ), e.getMessage() );
        }
    }

    public VersionlessArtifactRef asVersionlessArtifactRef()
        throws GalleyMavenException
    {
        try
        {
            return new VersionlessArtifactRef( asProjectRef(), getType(), getClassifier(), isOptional() );
        }
        catch ( IllegalArgumentException | InvalidVersionSpecificationException e )
        {
            final String classifier = getClassifier();
            throw new GalleyMavenException( "Cannot render VersionlessArtifactRef: %s:%s:%s%s. Reason: %s", e, getGroupId(), getArtifactId(),
                                            getRawType(), ( classifier == null ? "" : ":" + classifier ), e.getMessage() );
        }
    }

}