/*
 * Copyright 2014 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.world.generation.facets.base;

import org.terasology.math.Rect2i;
import org.terasology.math.Region3i;
import org.terasology.math.Vector3i;
import org.terasology.world.generation.WorldFacet2D;
import org.terasology.world.generation.WorldFacet3D;

/**
 * @author Immortius
 */
public class BaseFacet3D implements WorldFacet3D {

    private Region3i worldRegion;
    private Region3i relativeRegion;

    public BaseFacet3D(Region3i targetRegion, Vector3i border) {
        worldRegion = Region3i.createFromMinMax(new Vector3i(targetRegion.minX() - border.x, targetRegion.minY() - border.y, targetRegion.minZ() - border.z),
                new Vector3i(targetRegion.maxX() + border.x, targetRegion.maxY() + border.y, targetRegion.maxZ() + border.z));
        relativeRegion = Region3i.createFromMinMax(new Vector3i(-border.x, -border.y, -border.z),
                new Vector3i(targetRegion.sizeX() + border.x - 1, targetRegion.sizeY() + border.y - 1, targetRegion.sizeZ() + border.z - 1));
    }

    @Override
    public final Region3i getWorldRegion() {
        return worldRegion;
    }

    @Override
    public final Region3i getRelativeRegion() {
        return relativeRegion;
    }

    protected final int getRelativeIndex(int x, int y, int z) {
        if (!relativeRegion.encompasses(x, y, z)) {
            throw new ArrayIndexOutOfBoundsException(String.format("Out of bounds: (%d, %d, %d) for region %s", x, y, z, relativeRegion.toString()));
        }
        return x - relativeRegion.minX() + relativeRegion.sizeX() * (y - relativeRegion.minY() + relativeRegion.sizeY() * (z - relativeRegion.minY()));
    }

    protected final int getWorldIndex(int x, int y, int z) {
        if (!worldRegion.encompasses(x, y, z)) {
            throw new ArrayIndexOutOfBoundsException(String.format("Out of bounds: (%d, %d, %d) for region %s", x, y, z, worldRegion.toString()));
        }
        return x - worldRegion.minX() + worldRegion.sizeX() * (y - worldRegion.minY() + worldRegion.sizeY() * (z - worldRegion.minY()));
    }
}
