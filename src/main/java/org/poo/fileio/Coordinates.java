package org.poo.fileio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Represents a pair of coordinates with x and y values on the gameboard.
 * This class provides methods to manipulate and retrieve
 * the coordinates, as well as convert them into a JSON representation.
 */
public final class Coordinates {
   private int x, y;

   public Coordinates() {
   }

   /**
    * Creates a JSON representation of the coordinates using the provided ObjectMapper.
    *
    * @param mapper the {@link ObjectMapper} used to create the JSON object
    * @return an {@link ObjectNode} containing the x and y coordinates
    */
   public ObjectNode getCoordinatesObject(final ObjectMapper mapper) {
      ObjectNode coordinatesObject = mapper.createObjectNode();
      coordinatesObject.put("x", x);
      coordinatesObject.put("y", y);
      return coordinatesObject;
   }

   public int getX() {
      return x;
   }

   public void setX(final int x) {
      this.x = x;
   }

   public int getY() {
      return y;
   }

   public void setY(final int y) {
      this.y = y;
   }

   @Override
   public String toString() {
      return "Coordinates{"
              + "x="
              + x
              + ", y="
              + y
              + '}';
   }
}
