package edu.ufl.cise.cs1.robots;

import robocode.*;
import robocode.util.Utils;

import java.awt.*;
import java.awt.geom.Point2D;

public class SunsetRam extends TeamRobot {

    static double moveDirection = 1;
    static double oldEnemyHeading;

    public void run(){

        setAllColors(Color.PINK);
        setAdjustRadarForRobotTurn(true);
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setTurnRadarRightRadians(Double.POSITIVE_INFINITY);

    }

    public void onScannedRobot(ScannedRobotEvent e){

        if (isTeammate(e.getName())) {
            return;
        }


        ///One on One Radar Width Lock Inspired By http://robowiki.net/wiki/One_on_One_Radar and Movement inspired by http://mark.random-article.com/weber/java/robocode/lesson5.html
        double angleToEnemy = getHeadingRadians() + e.getBearingRadians();
        setTurnRightRadians(Utils.normalRelativeAngle(angleToEnemy - getHeadingRadians()));


        setMaxVelocity(400 / getTurnRemaining());
        setAhead(100 * moveDirection);


        //Circular Targeting Style Inspired By http://robowiki.net/wiki/Circular_Targeting
        double enemyHeading = e.getHeadingRadians();
        double enemyHeadingChange = enemyHeading - oldEnemyHeading;
        oldEnemyHeading = enemyHeading;

        double deltaTime = 0;
        double predictedX = getX() + e.getDistance() * Math.sin(angleToEnemy);
        double predictedY = getY() + e.getDistance() * Math.cos(angleToEnemy);
        while((++deltaTime) * 11 <  Point2D.Double.distance(getX(), getY(), predictedX, predictedY)){

            predictedX += Math.sin(enemyHeading) * e.getVelocity();
            predictedY += Math.cos(enemyHeading) * e.getVelocity();

            enemyHeading += enemyHeadingChange;

            if(	predictedX < 18.0 || predictedY < 18.0 || predictedX > getBattleFieldWidth() - 18.0 || predictedY > getBattleFieldHeight() - 18.0){

                predictedX = Math.min(Math.max(18.0, predictedX), getBattleFieldWidth() - 18.0);
                predictedY = Math.min(Math.max(18.0, predictedY), getBattleFieldHeight() - 18.0);
                break;
            }
        }

        double aim = Utils.normalAbsoluteAngle(Math.atan2(predictedX - getX(), predictedY - getY()));
        setTurnGunRightRadians(Utils.normalRelativeAngle(aim - getGunHeadingRadians()));
        setTurnRadarRightRadians(Utils.normalRelativeAngle(angleToEnemy - getRadarHeadingRadians()) * 2);

        fire(3);

    }

    public void onHitWall(HitWallEvent e){
        moveDirection *= -1;
    }

}