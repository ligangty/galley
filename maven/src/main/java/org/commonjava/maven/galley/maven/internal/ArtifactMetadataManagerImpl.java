/**
 * Copyright (C) 2013 Red Hat, Inc. (jdcasey@commonjava.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.commonjava.maven.galley.maven.internal;

import static org.commonjava.maven.galley.maven.util.ArtifactPathUtils.formatMetadataPath;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import org.commonjava.maven.atlas.ident.ref.ProjectRef;
import org.commonjava.maven.galley.TransferException;
import org.commonjava.maven.galley.TransferManager;
import org.commonjava.maven.galley.event.EventMetadata;
import org.commonjava.maven.galley.maven.ArtifactMetadataManager;
import org.commonjava.maven.galley.maven.ArtifactRules;
import org.commonjava.maven.galley.model.ConcreteResource;
import org.commonjava.maven.galley.model.Location;
import org.commonjava.maven.galley.model.Transfer;
import org.commonjava.maven.galley.model.VirtualResource;
import org.commonjava.maven.galley.spi.transport.LocationExpander;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArtifactMetadataManagerImpl
    implements ArtifactMetadataManager
{

    private final Logger logger = LoggerFactory.getLogger( getClass() );

    @Inject
    private TransferManager transferManager;

    @Inject
    private LocationExpander expander;

    protected ArtifactMetadataManagerImpl()
    {
    }

    public ArtifactMetadataManagerImpl( final TransferManager transferManager, final LocationExpander expander )
    {
        this.transferManager = transferManager;
        this.expander = expander;
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#delete(org.commonjava.maven.galley.model.Location, org.commonjava.maven.atlas.ident.ref.ProjectRef)
     */
    @Override
    public boolean delete( final Location location, final ProjectRef ref )
        throws TransferException
    {
        return delete( location, ref, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#delete(org.commonjava.maven.galley.model.Location, org.commonjava.maven.atlas.ident.ref.ProjectRef)
     */
    @Override
    public boolean delete( final Location location , final ProjectRef ref , final EventMetadata eventMetadata  )
        throws TransferException
    {
        return delete( location, ref, null, eventMetadata );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#delete(org.commonjava.maven.galley.model.Location, org.commonjava.maven.atlas.ident.ref.ProjectRef, java.lang.String)
     */
    @Override
    public boolean delete( final Location location, final ProjectRef ref, final String filename )
        throws TransferException
    {
        return delete( location, ref, filename, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#delete(org.commonjava.maven.galley.model.Location, org.commonjava.maven.atlas.ident.ref.ProjectRef, java.lang.String)
     */
    @Override
    public boolean delete( final Location location , final ProjectRef ref , final String filename , final EventMetadata eventMetadata  )
        throws TransferException
    {
        final String path = formatMetadataPath( ref, filename );
        return transferManager.deleteAll( new VirtualResource( expander.expand( location ), path ), eventMetadata );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#delete(org.commonjava.maven.galley.model.Location, java.lang.String)
     */
    @Override
    public boolean delete( final Location location, final String groupId )
        throws TransferException
    {
        return delete( location, groupId, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#delete(org.commonjava.maven.galley.model.Location, java.lang.String)
     */
    @Override
    public boolean delete( final Location location , final String groupId , final EventMetadata eventMetadata  )
        throws TransferException
    {
        return delete( location, groupId, null, eventMetadata );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#delete(org.commonjava.maven.galley.model.Location, java.lang.String, java.lang.String)
     */
    @Override
    public boolean delete( final Location location, final String groupId, final String filename )
        throws TransferException
    {
        return delete( location, groupId, filename, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#delete(org.commonjava.maven.galley.model.Location, java.lang.String, java.lang.String)
     */
    @Override
    public boolean delete( final Location location , final String groupId , final String filename , final EventMetadata eventMetadata  )
        throws TransferException
    {
        final String path = formatMetadataPath( groupId, filename );
        return transferManager.deleteAll( new VirtualResource( expander.expand( location ), path ), eventMetadata );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#deleteAll(java.util.List, java.lang.String)
     */
    @Override
    public boolean deleteAll( final List<? extends Location> locations, final String groupId )
        throws TransferException
    {
        return deleteAll( locations, groupId, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#deleteAll(java.util.List, java.lang.String)
     */
    @Override
    public boolean deleteAll( final List<? extends Location> locations , final String groupId , final EventMetadata eventMetadata  )
        throws TransferException
    {
        return deleteAll( locations, groupId, null, eventMetadata );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#deleteAll(java.util.List, java.lang.String, java.lang.String)
     */
    @Override
    public boolean deleteAll( final List<? extends Location> locations, final String groupId, final String filename )
        throws TransferException
    {
        return deleteAll( locations, groupId, filename, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#deleteAll(java.util.List, java.lang.String, java.lang.String)
     */
    @Override
    public boolean deleteAll( final List<? extends Location> locations , final String groupId , final String filename , final EventMetadata eventMetadata  )
        throws TransferException
    {
        return transferManager.deleteAll( new VirtualResource( expander.expand( locations ),
                                                               formatMetadataPath( groupId, filename ) ), eventMetadata );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#deleteAll(java.util.List, org.commonjava.maven.atlas.ident.ref.ProjectRef)
     */
    @Override
    public boolean deleteAll( final List<? extends Location> locations, final ProjectRef ref )
        throws TransferException
    {
        return deleteAll( locations, ref, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#deleteAll(java.util.List, org.commonjava.maven.atlas.ident.ref.ProjectRef)
     */
    @Override
    public boolean deleteAll( final List<? extends Location> locations , final ProjectRef ref , final EventMetadata eventMetadata  )
        throws TransferException
    {
        return deleteAll( locations, ref, null, eventMetadata );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#deleteAll(java.util.List, org.commonjava.maven.atlas.ident.ref.ProjectRef, java.lang.String)
     */
    @Override
    public boolean deleteAll( final List<? extends Location> locations, final ProjectRef ref, final String filename )
        throws TransferException
    {
        return deleteAll( locations, ref, filename, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#deleteAll(java.util.List, org.commonjava.maven.atlas.ident.ref.ProjectRef, java.lang.String)
     */
    @Override
    public boolean deleteAll( final List<? extends Location> locations , final ProjectRef ref , final String filename , final EventMetadata eventMetadata  )
        throws TransferException
    {
        return transferManager.deleteAll( new VirtualResource( expander.expand( locations ),
                                                               formatMetadataPath( ref, filename ) ), eventMetadata );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#retrieve(org.commonjava.maven.galley.model.Location, java.lang.String)
     */
    @Override
    public Transfer retrieve( final Location location, final String groupId )
        throws TransferException
    {
        return retrieve( location, groupId, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#retrieve(org.commonjava.maven.galley.model.Location, java.lang.String)
     */
    @Override
    public Transfer retrieve( final Location location , final String groupId , final EventMetadata eventMetadata  )
        throws TransferException
    {
        return retrieve( location, groupId, null, eventMetadata );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#retrieve(org.commonjava.maven.galley.model.Location, java.lang.String, java.lang.String)
     */
    @Override
    public Transfer retrieve( final Location location, final String groupId, final String filename )
        throws TransferException
    {
        return retrieve( location, groupId, filename, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#retrieve(org.commonjava.maven.galley.model.Location, java.lang.String, java.lang.String)
     */
    @Override
    public Transfer retrieve( final Location location , final String groupId , final String filename , final EventMetadata eventMetadata  )
        throws TransferException
    {
        return transferManager.retrieveFirst( new VirtualResource( expander.expand( location ),
                                                                   formatMetadataPath( groupId, filename ) ),
                                              eventMetadata );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#retrieve(org.commonjava.maven.galley.model.Location, org.commonjava.maven.atlas.ident.ref.ProjectRef)
     */
    @Override
    public Transfer retrieve( final Location location, final ProjectRef ref )
        throws TransferException
    {
        return retrieve( location, ref, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#retrieve(org.commonjava.maven.galley.model.Location, org.commonjava.maven.atlas.ident.ref.ProjectRef)
     */
    @Override
    public Transfer retrieve( final Location location , final ProjectRef ref , final EventMetadata eventMetadata  )
        throws TransferException
    {
        return retrieve( location, ref, null, eventMetadata );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#retrieve(org.commonjava.maven.galley.model.Location, org.commonjava.maven.atlas.ident.ref.ProjectRef, java.lang.String)
     */
    @Override
    public Transfer retrieve( final Location location, final ProjectRef ref, final String filename )
        throws TransferException
    {
        return retrieve( location, ref, filename, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#retrieve(org.commonjava.maven.galley.model.Location, org.commonjava.maven.atlas.ident.ref.ProjectRef, java.lang.String)
     */
    @Override
    public Transfer retrieve( final Location location , final ProjectRef ref , final String filename , final EventMetadata eventMetadata  )
        throws TransferException
    {
        return transferManager.retrieveFirst( new VirtualResource( expander.expand( location ),
                                                                   formatMetadataPath( ref, filename ) ), eventMetadata );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#retrieveAll(java.util.List, java.lang.String)
     */
    @Override
    public List<Transfer> retrieveAll( final List<? extends Location> locations, final String groupId )
        throws TransferException
    {
        return retrieveAll( locations, groupId, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#retrieveAll(java.util.List, java.lang.String)
     */
    @Override
    public List<Transfer> retrieveAll( final List<? extends Location> locations , final String groupId , final EventMetadata eventMetadata  )
        throws TransferException
    {
        return retrieveAll( locations, groupId, null, eventMetadata );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#retrieveAll(java.util.List, java.lang.String, java.lang.String)
     */
    @Override
    public List<Transfer> retrieveAll( final List<? extends Location> locations, final String groupId,
                                       final String filename )
        throws TransferException
    {
        return retrieveAll( locations, groupId, filename, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#retrieveAll(java.util.List, java.lang.String, java.lang.String)
     */
    @Override
    public List<Transfer> retrieveAll( final List<? extends Location> locations , final String groupId ,
                                       final String filename , final EventMetadata eventMetadata  )
        throws TransferException
    {
        return transferManager.retrieveAll( new VirtualResource( expander.expand( locations ),
                                                                 formatMetadataPath( groupId, filename ) ),
                                            eventMetadata );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#retrieveAll(java.util.List, org.commonjava.maven.atlas.ident.ref.ProjectRef)
     */
    @Override
    public List<Transfer> retrieveAll( final List<? extends Location> locations, final ProjectRef ref )
        throws TransferException
    {
        return retrieveAll( locations, ref, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#retrieveAll(java.util.List, org.commonjava.maven.atlas.ident.ref.ProjectRef)
     */
    @Override
    public List<Transfer> retrieveAll( final List<? extends Location> locations , final ProjectRef ref , final EventMetadata eventMetadata  )
        throws TransferException
    {
        return retrieveAll( locations, ref, null, eventMetadata );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#retrieveAll(java.util.List, org.commonjava.maven.atlas.ident.ref.ProjectRef, java.lang.String)
     */
    @Override
    public List<Transfer> retrieveAll( final List<? extends Location> locations, final ProjectRef ref,
                                       final String filename )
        throws TransferException
    {
        return retrieveAll( locations, ref, filename, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#retrieveAll(java.util.List, org.commonjava.maven.atlas.ident.ref.ProjectRef, java.lang.String)
     */
    @Override
    public List<Transfer> retrieveAll( final List<? extends Location> locations , final ProjectRef ref ,
                                       final String filename , final EventMetadata eventMetadata  )
        throws TransferException
    {
        return transferManager.retrieveAll( new VirtualResource( expander.expand( locations ),
                                                                 formatMetadataPath( ref, filename ) ), eventMetadata );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#retrieveFirst(java.util.List, java.lang.String)
     */
    @Override
    public Transfer retrieveFirst( final List<? extends Location> locations, final String groupId )
        throws TransferException
    {
        return retrieveFirst( locations, groupId, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#retrieveFirst(java.util.List, java.lang.String)
     */
    @Override
    public Transfer retrieveFirst( final List<? extends Location> locations , final String groupId , final EventMetadata eventMetadata  )
        throws TransferException
    {
        return retrieveFirst( locations, groupId, null, eventMetadata );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#retrieveFirst(java.util.List, java.lang.String, java.lang.String)
     */
    @Override
    public Transfer retrieveFirst( final List<? extends Location> locations, final String groupId, final String filename )
        throws TransferException
    {
        return retrieveFirst( locations, groupId, filename, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#retrieveFirst(java.util.List, java.lang.String, java.lang.String)
     */
    @Override
    public Transfer retrieveFirst( final List<? extends Location> locations , final String groupId , final String filename , final EventMetadata eventMetadata  )
        throws TransferException
    {
        return transferManager.retrieveFirst( new VirtualResource( expander.expand( locations ),
                                                                   formatMetadataPath( groupId, filename ) ),
                                              eventMetadata );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#retrieveFirst(java.util.List, org.commonjava.maven.atlas.ident.ref.ProjectRef)
     */
    @Override
    public Transfer retrieveFirst( final List<? extends Location> locations, final ProjectRef ref )
        throws TransferException
    {
        return retrieveFirst( locations, ref, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#retrieveFirst(java.util.List, org.commonjava.maven.atlas.ident.ref.ProjectRef)
     */
    @Override
    public Transfer retrieveFirst( final List<? extends Location> locations , final ProjectRef ref , final EventMetadata eventMetadata  )
        throws TransferException
    {
        return retrieveFirst( locations, ref, null, eventMetadata );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#retrieveFirst(java.util.List, org.commonjava.maven.atlas.ident.ref.ProjectRef, java.lang.String)
     */
    @Override
    public Transfer retrieveFirst( final List<? extends Location> locations, final ProjectRef ref, final String filename )
        throws TransferException
    {
        return retrieveFirst( locations, ref, filename, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#retrieveFirst(java.util.List, org.commonjava.maven.atlas.ident.ref.ProjectRef, java.lang.String)
     */
    @Override
    public Transfer retrieveFirst( final List<? extends Location> locations , final ProjectRef ref , final String filename , final EventMetadata eventMetadata  )
        throws TransferException
    {
        return transferManager.retrieveFirst( new VirtualResource( expander.expand( locations ),
                                                                   formatMetadataPath( ref, filename ) ), eventMetadata );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#store(org.commonjava.maven.galley.model.Location, java.lang.String, java.io.InputStream)
     */
    @Override
    public Transfer store( final Location location, final String groupId, final InputStream stream )
        throws TransferException
    {
        return store( location, groupId, stream, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#store(org.commonjava.maven.galley.model.Location, java.lang.String, java.io.InputStream)
     */
    @Override
    public Transfer store( final Location location , final String groupId , final InputStream stream , final EventMetadata eventMetadata  )
        throws TransferException
    {
        return store( location, groupId, null, stream, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#store(org.commonjava.maven.galley.model.Location, java.lang.String, java.lang.String, java.io.InputStream)
     */
    @Override
    public Transfer store( final Location location, final String groupId, final String filename,
                           final InputStream stream )
        throws TransferException
    {
        return store( location, groupId, filename, stream, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#store(org.commonjava.maven.galley.model.Location, java.lang.String, java.lang.String, java.io.InputStream)
     */
    @Override
    public Transfer store( final Location location , final String groupId , final String filename ,
                           final InputStream stream , final EventMetadata eventMetadata  )
        throws TransferException
    {
        final VirtualResource virt =
            new VirtualResource( expander.expand( location ), formatMetadataPath( groupId, filename ) );
        final ConcreteResource selected = ArtifactRules.selectStorageResource( virt );

        if ( selected == null )
        {
            logger.warn( "Cannot deploy. No valid deploy points in group." );
            throw new TransferException( "No deployment locations available for: {}", virt.toConcreteResources() );
        }

        return transferManager.store( selected, stream, eventMetadata );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#store(org.commonjava.maven.galley.model.Location, org.commonjava.maven.atlas.ident.ref.ProjectRef, java.io.InputStream)
     */
    @Override
    public Transfer store( final Location location, final ProjectRef ref, final InputStream stream )
        throws TransferException
    {
        return store( location, ref, stream, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#store(org.commonjava.maven.galley.model.Location, org.commonjava.maven.atlas.ident.ref.ProjectRef, java.io.InputStream)
     */
    @Override
    public Transfer store( final Location location , final ProjectRef ref , final InputStream stream , final EventMetadata eventMetadata  )
        throws TransferException
    {
        return store( location, ref, null, stream, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#store(org.commonjava.maven.galley.model.Location, org.commonjava.maven.atlas.ident.ref.ProjectRef, java.lang.String, java.io.InputStream)
     */
    @Override
    public Transfer store( final Location location, final ProjectRef ref, final String filename,
                           final InputStream stream )
        throws TransferException
    {
        return store( location, ref, filename, stream, new EventMetadata() );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#store(org.commonjava.maven.galley.model.Location, org.commonjava.maven.atlas.ident.ref.ProjectRef, java.lang.String, java.io.InputStream)
     */
    @Override
    public Transfer store( final Location location , final ProjectRef ref , final String filename ,
                           final InputStream stream , final EventMetadata eventMetadata  )
        throws TransferException
    {
        final VirtualResource virt =
            new VirtualResource( expander.expand( location ), formatMetadataPath( ref, filename ) );
        final ConcreteResource selected = ArtifactRules.selectStorageResource( virt );

        if ( selected == null )
        {
            logger.warn( "Cannot deploy. No valid deploy points in group." );
            throw new TransferException( "No deployment locations available for: {}", virt.toConcreteResources() );
        }

        return transferManager.store( selected, stream, eventMetadata );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#publish(org.commonjava.maven.galley.model.Location, java.lang.String, java.io.InputStream, long)
     */
    @Override
    public boolean publish( final Location location, final String groupId, final InputStream stream, final long length )
        throws TransferException
    {
        return publish( location, groupId, null, stream, length, null );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#publish(org.commonjava.maven.galley.model.Location, java.lang.String, java.lang.String, java.io.InputStream, long, java.lang.String)
     */
    @Override
    public boolean publish( final Location location, final String groupId, final String filename,
                            final InputStream stream, final long length, final String contentType )
        throws TransferException
    {
        return transferManager.publish( new ConcreteResource( location, formatMetadataPath( groupId, filename ) ),
                                        stream, length, contentType );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#publish(org.commonjava.maven.galley.model.Location, org.commonjava.maven.atlas.ident.ref.ProjectRef, java.io.InputStream, long)
     */
    @Override
    public boolean publish( final Location location, final ProjectRef ref, final InputStream stream, final long length )
        throws TransferException
    {
        return publish( location, ref, null, stream, length, null );
    }

    /* (non-Javadoc)
     * @see org.commonjava.maven.galley.ArtifactMetadataManager#publish(org.commonjava.maven.galley.model.Location, org.commonjava.maven.atlas.ident.ref.ProjectRef, java.lang.String, java.io.InputStream, long, java.lang.String)
     */
    @Override
    public boolean publish( final Location location, final ProjectRef ref, final String filename,
                            final InputStream stream, final long length, final String contentType )
        throws TransferException
    {
        return transferManager.publish( new ConcreteResource( location, formatMetadataPath( ref, filename ) ), stream,
                                        length, contentType );
    }

}
